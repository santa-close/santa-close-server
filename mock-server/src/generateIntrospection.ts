import { readFileSync, writeFileSync } from 'fs';
import { join } from 'path';
import { buildSchema, introspectionFromSchema } from 'graphql';

async function generateIntrospection() {
  const schema = readFileSync(
    join(__dirname, '../../app/src/main/resources/graphql/schema.graphql')
  );
  const introspectionQuery = introspectionFromSchema(
    buildSchema(schema.toString())
  );
  writeFileSync(
    __dirname + '/introspection.json',
    JSON.stringify(introspectionQuery)
  );
}

generateIntrospection().catch(console.error);
