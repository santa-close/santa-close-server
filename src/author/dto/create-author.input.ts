import { InputType, Int, Field } from '@nestjs/graphql';

@InputType()
export class CreateAuthorInput {
  @Field(() => Int, { description: 'Example field (placeholder)' })
  exampleField: number;
}
