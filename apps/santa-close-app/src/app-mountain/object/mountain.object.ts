import { ObjectType, Field, ID, Int } from '@nestjs/graphql';
import { Coordinate } from './coordinate.object';

@ObjectType()
export class Mountain {
  @Field(() => ID)
  id: string;

  @Field()
  name: string;

  @Field()
  address: string;

  @Field()
  rate: number;

  @Field(() => Int)
  viewCount: number;

  @Field(() => Int)
  bookmarkCount: number;

  @Field(() => Int)
  reviewCount: number;

  @Field(() => Coordinate)
  coordinates: Coordinate;
}
