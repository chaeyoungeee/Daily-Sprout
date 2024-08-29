export default function HabitProgressBar({percent}) {
    return (
      <div className="flex rounded bg-gray-800">
        <div
          className="h-[8px] rounded bg-green-700"
          style={{ width: `${percent}%` }}
        />
      </div>
    );
}