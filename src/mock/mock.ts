import { datatype, lorem } from 'faker';
import { Author } from '../author/entities/author.entity';

export default {
  String: () => lorem.word(),
  Int: () => datatype.number(),
  Authors: (): Author => ({
    exampleField: datatype.number(),
    strField: lorem.word(),
  }),
};
