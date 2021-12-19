import { Field, InputType } from '@nestjs/graphql';
import { CoordinateInput } from './coordinate.input';

@InputType()
export class MountainCountInput {
  @Field(() => CoordinateInput)
  nw: CoordinateInput;

  @Field(() => CoordinateInput)
  se: CoordinateInput;
}
