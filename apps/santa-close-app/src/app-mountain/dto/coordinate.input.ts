import { Field, InputType } from '@nestjs/graphql';

@InputType()
export class CoordinateInput {
  @Field()
  longitude: number;

  @Field()
  latitude: number;
}
