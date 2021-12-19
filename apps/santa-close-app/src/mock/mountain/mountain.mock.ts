import { Mountain } from '../../app-mountain/object/mountain.object';
import { datatype, lorem, random } from 'faker';
import { Coordinate } from '../../app-mountain/object/coordinate.object';

export const mountainMock: (latitude: number, longitude: number) => Mountain = (
  latitude,
  longitude,
) => {
  const object = new Mountain();
  object.name = random.word();
  object.address = lorem.sentence();
  object.rate = datatype.float({ max: 5, min: 0, precision: 5 });
  object.coordinates = new Coordinate();
  object.coordinates.latitude = latitude;
  object.coordinates.longitude = longitude;

  return object;
};
