import { datatype, lorem } from 'faker';
import { mountainMock } from './mountain/mountain.mock';

export default {
  ID: () => `${datatype.number()}`,
  String: () => lorem.word(),
  Int: () => datatype.number(),
  Boolean: () => datatype.boolean(),
  Query: {
    mountains: () => [
      mountainMock(37.378349, 127.016167),
      mountainMock(37.377906, 127.011916),
      mountainMock(37.37586, 127.015909),
      mountainMock(37.379611, 127.022136),
    ],
    mountain: () => mountainMock(37.379611, 127.022136),
  },
};
