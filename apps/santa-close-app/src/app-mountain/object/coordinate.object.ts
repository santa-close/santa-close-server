import { ObjectType, Field } from '@nestjs/graphql';

@ObjectType()
export class Coordinate {
  @Field()
  longitude: number;

  @Field()
  latitude: number;
}
