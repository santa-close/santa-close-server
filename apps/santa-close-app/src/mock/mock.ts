import { datatype, lorem } from 'faker';
import { mountainMock } from './mountain/mountain.mock';

export default {
  ID: () => `${datatype.number()}`,
  String: () => lorem.word(),
  Int: () => datatype.number(),
  Boolean: () => datatype.boolean(),
  Mountains: () => [
    mountainMock(),
    mountainMock(),
    mountainMock(),
    mountainMock(),
  ],
  Mountain: () => mountainMock(),
};
