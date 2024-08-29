import moment from "moment";

export default function formatDateToISO(date) {
  return moment(date).format("YYYY-MM-DD");
}
