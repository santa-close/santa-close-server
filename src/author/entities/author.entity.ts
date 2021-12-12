import { ObjectType, Field, Int } from '@nestjs/graphql';

@ObjectType()
export class Author {
  @Field(() => Int, { description: 'Example field (placeholder)' })
  exampleField: number;

  @Field({ description: 'Example field (placeholder)' })
  strField: string;
}
