import { format } from 'date-fns';

export function toDateTime(date: Date) {
  return format(date, 'yyyy-MM-dd HH:mm:ss');
}
