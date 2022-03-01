import { toDateTime } from './util';
import { addDays } from 'date-fns';
import faker from '@faker-js/faker';

export const signIn = () => ({
  accessToken: [
    faker.random.alphaNumeric(30),
    faker.random.alphaNumeric(50),
    faker.random.alphaNumeric(30),
  ].join('.'),
  expiredAt: toDateTime(addDays(new Date(), 30)),
});
