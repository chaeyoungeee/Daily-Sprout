const getMondayOfWeekContainingFirstDay = (year, month) => {
  // 주어진 년도와 월의 1일을 기준으로 날짜 객체를 생성
  const firstDayOfMonth = new Date(year, month - 1, 1);

  const dayOfWeek = firstDayOfMonth.getDay();
  const dayOfMonth = firstDayOfMonth.getDate();

  const daysToMonday = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;

  const mondayOfWeek = new Date(year, month - 1, dayOfMonth + daysToMonday);

  return mondayOfWeek;
};

export default getMondayOfWeekContainingFirstDay;
