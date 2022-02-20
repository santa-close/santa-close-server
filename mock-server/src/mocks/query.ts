import faker from '@faker-js/faker';

export const sample = () => ({
  name: faker.name.findName(),
  price: faker.datatype.number({ min: 0, max: 1000 }),
  status: faker.random.arrayElement(['OPEN', 'CLOSE']),
});
