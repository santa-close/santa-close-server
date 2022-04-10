import { categories, sample } from './query';
import { signIn } from './mutation';
import { toDateTime } from './util';
import faker from '@faker-js/faker';

export const mocks = {
  ID: () => `${faker.datatype.number({ min: 1, max: 10000 })}`,
  Int: () => faker.datatype.number({ min: 1, max: 10000 }),
  Float: () => faker.datatype.float(),
  String: () => faker.datatype.string(),
  Boolean: () => faker.datatype.boolean(),
  DateTime: () => toDateTime(faker.date.recent()),
  Query: {
    sample,
    categories,
  },
  Mutation: {
    signIn,
    createMountainReview: () => true,
    createRestaurantReview: () => true,
  },
};
