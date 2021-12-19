import { Mountain } from '../../app-mountain/object/mountain.object';
import { datatype, lorem, random } from 'faker';
import { Coordinate } from '../../app-mountain/object/coordinate.object';

export const mountainMock: () => Mountain = () => {
  const object = new Mountain();
  object.name = random.word();
  object.address = lorem.sentence();
  object.rate = datatype.float({ max: 5, min: 0, precision: 5 });
  object.coordinates = new Coordinate();
  object.coordinates.longitude = datatype.float();
  object.coordinates.latitude = datatype.float();

  return object;
};
