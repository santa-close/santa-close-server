import { CreateAppMountainInput } from './create-app-mountain.input';
import { InputType, Field, Int, PartialType } from '@nestjs/graphql';

@InputType()
export class UpdateAppMountainInput extends PartialType(
  CreateAppMountainInput,
) {
  @Field(() => Int)
  id: number;
}
