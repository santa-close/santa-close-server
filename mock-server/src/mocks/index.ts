import { sample } from './query';
import { signIn } from './mutation';
import { chance } from './chance';
import { toDateTime } from './util';

export const mocks = {
  Int: () => chance.integer({ min: 1, max: 10000 }),
  Float: () => chance.floating(),
  String: () => chance.string(),
  Boolean: () => chance.bool(),
  DateTime: () => toDateTime(chance.date()),
  Query: {
    sample,
  },
  Mutation: {
    signIn,
  },
};
