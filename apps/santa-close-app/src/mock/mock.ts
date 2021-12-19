import { datatype, lorem } from 'faker';

export default {
  String: () => lorem.word(),
  Int: () => datatype.number(),
};
