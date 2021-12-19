import { Module } from '@nestjs/common';
import { SantaCloseAppController } from './santa-close-app.controller';
import { SantaCloseAppService } from './santa-close-app.service';
import { GraphQLModule } from '@nestjs/graphql';
import { join } from 'path';
import { ApolloServerPluginLandingPageLocalDefault } from 'apollo-server-core';
import mocks from './mock/mock';
import { getTestTypeORMModule } from '@app/entity/getTestTypeORMModule';
import { EmptyModule } from '@app/web-common/empty.module';

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
    process.env.NODE_ENV === 'mock' ? getTestTypeORMModule() : EmptyModule,
  ],
  controllers: [SantaCloseAppController],
  providers: [SantaCloseAppService],
})
export class SantaCloseAppModule {}
