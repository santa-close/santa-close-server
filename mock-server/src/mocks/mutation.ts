import { toDateTime } from './util';
import { addDays } from 'date-fns';

export const signIn = () => ({
  accessToken: 123,
  expiredAt: toDateTime(addDays(new Date(), 30)),
});
