import { Module } from '@nestjs/common';
import { GraphQLModule } from '@nestjs/graphql';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { join } from 'path';
import { ApolloServerPluginLandingPageLocalDefault } from 'apollo-server-core';
import { AuthorModule } from './author/author.module';
import { TypeOrmModule } from '@nestjs/typeorm';

@Module({
  imports: [
    GraphQLModule.forRoot({
      autoSchemaFile: join(process.cwd(), 'src/schema.gql'),
      sortSchema: true,
      debug: true,
      playground: false,
      plugins: [ApolloServerPluginLandingPageLocalDefault],
      mocks: {
        String: () => 'test',
        Int: () => 123,
        Authors: () => ({
          exampleField: 3333,
          strField: 'testtest',
        }),
      },
    }),
    TypeOrmModule.forRoot({
      type: 'mysql',
      host: 'localhost',
      port: 3306,
      username: 'root',
      password: 'test',
      database: 'test',
      entities: [],
      synchronize: true,
    }),
    AuthorModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
