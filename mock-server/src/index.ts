import { ApolloServer, gql } from 'apollo-server';
import { readFileSync } from 'fs';
import { join } from 'path';

const typeDefs = gql`
  ${readFileSync(
    join(__dirname, '../../app/src/main/resources/graphql/schema.graphql')
  )}
`;

const server = new ApolloServer({
  typeDefs,
  mocks: true,
});

server.listen().then(({ url }) => console.log(`🚀 Server ready at ${url}`));
