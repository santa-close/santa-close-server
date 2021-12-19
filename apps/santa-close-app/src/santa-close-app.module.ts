import { DynamicModule, Module } from '@nestjs/common';
import { SantaCloseAppController } from './santa-close-app.controller';
import { SantaCloseAppService } from './santa-close-app.service';
import { GraphQLModule } from '@nestjs/graphql';
import { join } from 'path';
import { ApolloServerPluginLandingPageLocalDefault } from 'apollo-server-core';
import mocks from './mock/mock';
import { getTestTypeORMModule } from '@app/entity/getTestTypeORMModule';

@Module({
  imports: [
    GraphQLModule.forRoot({
      autoSchemaFile: join(process.cwd(), 'src/schema.gql'),
      sortSchema: true,
      debug: true,
      playground: false,
      plugins: [ApolloServerPluginLandingPageLocalDefault],
      mocks: process.env.NODE_ENV === 'mock' ? mocks : undefined,
    }),
    getTestTypeORMModule(),
  ],
  controllers: [SantaCloseAppController],
  providers: [SantaCloseAppService],
})
export class SantaCloseAppModule {}
