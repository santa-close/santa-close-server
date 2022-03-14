import faker from '@faker-js/faker';
import { readFileSync } from 'fs';
import { join } from 'path';

export const sample = () => ({
  name: faker.name.findName(),
  price: faker.datatype.number({ min: 0, max: 1000 }),
  status: faker.random.arrayElement(['OPEN', 'CLOSE']),
});

export const categories = () =>
  JSON.parse(
    `${readFileSync(
      join(__dirname, '../../../app/src/main/resources/graphql/categories.json')
    )}`
  ).data.categories;
