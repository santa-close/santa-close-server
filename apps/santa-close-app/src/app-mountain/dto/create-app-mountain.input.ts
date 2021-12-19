import { InputType, Int, Field } from '@nestjs/graphql';

@InputType()
export class CreateAppMountainInput {
  @Field(() => Int, { description: 'Example field (placeholder)' })
  exampleField: number;
}
