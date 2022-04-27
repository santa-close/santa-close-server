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

export const locations = () => [
  {
    __typename: 'MountainAppLocation',
    type: 'MOUNTAIN',
    coordinate: {
      latitude: 37.38018,
      longitude: 127.012006,
    },
  },
  {
    __typename: 'RestaurantAppLocation',
    type: 'RESTAURANT',
    coordinate: {
      latitude: 37.378974,
      longitude: 127.009305,
    },
  },
  {
    __typename: 'RestaurantAppLocation',
    type: 'RESTAURANT',
    coordinate: {
      latitude: 37.383322,
      longitude: 127.011579,
    },
  },
  {
    __typename: 'RestaurantAppLocation',
    type: 'RESTAURANT',
    coordinate: {
      latitude: 37.380593,
      longitude: 127.013768,
    },
  },
];
