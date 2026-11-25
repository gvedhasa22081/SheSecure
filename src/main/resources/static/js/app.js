// Small progressive enhancement: set the incident date field to today's date on first visit.
document.addEventListener('DOMContentLoaded', () => {
  const dateField = document.querySelector('input[name="incidentDate"]');
  if (dateField && !dateField.value) dateField.value = new Date().toISOString().slice(0, 10);
});
