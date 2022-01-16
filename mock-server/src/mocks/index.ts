import { sample } from './sample';
import { chance } from './chance';

export const mocks = {
  Int: () => chance.integer({ min: 1, max: 10000 }),
  Float: () => chance.floating(),
  String: () => chance.string(),
  Boolean: () => chance.bool(),
  Query: {
    sample,
  },
};
