import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import TextField from "../../components/common/TextField";
import { screeningsApi } from "../../api/screeningsApi";
import type { Screening } from "../../types/screening";

const ScreeningFormPage: React.FC = () => {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const navigate = useNavigate();

  const [form, setForm] = useState({
    programId: "",
    title: "",
    genre: "",
    description: "",
    room: "",
    scheduledTime: ""
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const load = async () => {
      if (!isEdit || !id) return;
      setLoading(true);
      try {
        const s: Screening = await screeningsApi.get(Number(id));
        setForm({
          programId: String(s.programId),
          title: s.title,
          genre: s.genre,
          description: s.description,
          room: s.room,
          scheduledTime: s.scheduledTime.slice(0, 16) // για input type="datetime-local"
        });
      } catch (err) {
        console.error(err);
        setError("Αποτυχία φόρτωσης προβολής");
      } finally {
        setLoading(false);
      }
    };
    void load();
  }, [id, isEdit]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    const payload = {
      programId: Number(form.programId),
      title: form.title,
      genre: form.genre,
      description: form.description,
      room: form.room,
      scheduledTime: form.scheduledTime
    };

    try {
      if (isEdit && id) {
        await screeningsApi.update(Number(id), payload);
      } else {
        await screeningsApi.create(payload);
      }
      navigate("/screenings");
    } catch (err) {
      console.error(err);
      setError("Αποτυχία αποθήκευσης προβολής");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1>{isEdit ? "Edit Screening" : "New Screening"}</h1>
      {error && <div className="error-box">{error}</div>}

      <form className="form" onSubmit={handleSubmit}>
        <TextField
          label="Program ID"
          name="programId"
          value={form.programId}
          onChange={handleChange}
          required
        />
        <TextField
          label="Title"
          name="title"
          value={form.title}
          onChange={handleChange}
          required
        />
        <TextField
          label="Genre"
          name="genre"
          value={form.genre}
          onChange={handleChange}
        />
        <div className="form-field">
          <label className="form-label" htmlFor="description">
            Description
          </label>
          <textarea
            id="description"
            name="description"
            className="form-textarea"
            value={form.description}
            onChange={handleChange}
          />
        </div>
        <TextField
          label="Room"
          name="room"
          value={form.room}
          onChange={handleChange}
        />
        <TextField
          label="Scheduled Time"
          name="scheduledTime"
          type="datetime-local"
          value={form.scheduledTime}
          onChange={handleChange}
          required
        />

        <button type="submit" disabled={loading}>
          {loading ? "Saving..." : "Save"}
        </button>
      </form>
    </div>
  );
};

export default ScreeningFormPage;
