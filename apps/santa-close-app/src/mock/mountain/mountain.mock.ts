import { MountainItem } from '../../app-mountain/object/mountain-item.object';
import { datatype, lorem, random } from 'faker';
import { Coordinate } from '../../app-mountain/object/coordinate.object';

export const mountainMock: (
  latitude: number,
  longitude: number,
) => MountainItem = (latitude, longitude) => {
  const object = new MountainItem();
  object.name = random.word();
  object.address = lorem.sentence();
  object.rate = datatype.float({ max: 5, min: 0, precision: 5 });
  object.coordinates = new Coordinate();
  object.coordinates.latitude = latitude;
  object.coordinates.longitude = longitude;

  return object;
};
