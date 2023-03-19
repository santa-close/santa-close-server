import { readFileSync } from 'fs';
import { join } from 'path';
import { addMocksToSchema } from '@graphql-tools/mock';
import express from 'express';
import http from 'http';
import { faker } from '@faker-js/faker';
import cors from 'cors';
import gql from 'graphql-tag';
import { ApolloServerPluginDrainHttpServer } from '@apollo/server/plugin/drainHttpServer';
import { ApolloServer } from '@apollo/server';
import { makeExecutableSchema } from '@graphql-tools/schema';
import { mocks } from './mocks';
import { json } from 'body-parser';
import { expressMiddleware } from '@apollo/server/express4';

async function startApolloServer() {
  const app = express();
  const httpServer = http.createServer(app);

  const typeDefs = gql`
    ${readFileSync(
      join(
        __dirname,
        '../../server-app/src/main/resources/graphql/schema.graphqls'
      )
    )}
  `;
  const server = new ApolloServer({
    schema: addMocksToSchema({
      schema: makeExecutableSchema({ typeDefs }),
      mocks,
    }),
    plugins: [ApolloServerPluginDrainHttpServer({ httpServer })],
  });
  await server.start();

  app.use(
    '/graphql',
    cors<cors.CorsRequest>(),
    json(),
    expressMiddleware(server)
  );
  app.post('/api/image', (req, res) =>
    res.json({ url: faker.image.imageUrl() })
  );

  await new Promise<void>((resolve) =>
    httpServer.listen({ port: 4000 }, resolve)
  );
  console.log(`🚀 Server ready at http://localhost:4000/graphql`);
}

startApolloServer().catch(console.error);
