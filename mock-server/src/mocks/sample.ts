import { chance } from './chance';

export const sample = () => ({
  name: chance.name(),
  price: chance.integer({ min: 0, max: 1000 }),
  status: chance.pickone(['OPEN', 'CLOSE']),
});
