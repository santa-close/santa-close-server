import { ObjectType, Field, Int } from '@nestjs/graphql';

@ObjectType()
export class AppMountain {
  @Field(() => Int, { description: 'Example field (placeholder)' })
  exampleField: number;
}
