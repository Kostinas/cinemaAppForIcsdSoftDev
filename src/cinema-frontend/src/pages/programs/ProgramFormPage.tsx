import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import TextField from "../../components/common/TextField";
import { programsApi } from "../../api/programsApi";
import type { Program } from "../../types/program";

const ProgramFormPage: React.FC = () => {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    description: "",
    startDate: "",
    endDate: ""
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const load = async () => {
      if (!isEdit || !id) return;
      setLoading(true);
      try {
        const program: Program = await programsApi.get(Number(id));
        setForm({
          name: program.name,
          description: program.description,
          startDate: program.startDate,
          endDate: program.endDate
        });
      } catch (err) {
        console.error(err);
        setError("Αποτυχία φόρτωσης program");
      } finally {
        setLoading(false);
      }
    };
    void load();
  }, [id, isEdit]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      if (isEdit && id) {
        await programsApi.update(Number(id), form);
      } else {
        await programsApi.create(form);
      }
      navigate("/programs");
    } catch (err) {
      console.error(err);
      setError("Αποτυχία αποθήκευσης");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1>{isEdit ? "Edit Program" : "New Program"}</h1>
      {error && <div className="error-box">{error}</div>}

      <form className="form" onSubmit={handleSubmit}>
        <TextField
          label="Name"
          name="name"
          value={form.name}
          onChange={handleChange}
          required
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
          label="Start Date"
          name="startDate"
          type="date"
          value={form.startDate}
          onChange={handleChange}
          required
        />
        <TextField
          label="End Date"
          name="endDate"
          type="date"
          value={form.endDate}
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

export default ProgramFormPage;
