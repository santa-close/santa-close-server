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
    'introspection.json',
    JSON.stringify({ data: introspectionQuery })
  );
}

generateIntrospection().catch(console.error);
