import { TypeOrmModule } from '@nestjs/typeorm';
import * as path from 'path';
import { ConstraintSnakeNamingStrategy } from '@app/entity/ConstraintSnakeNamingStrategy';
import { DynamicModule } from '@nestjs/common';

export function getTestTypeORMModule(): DynamicModule {
  return TypeOrmModule.forRoot({
    type: 'mysql',
    host: 'localhost',
    port: 3306,
    username: 'test',
    password: 'test',
    database: 'test',
    entities: [path.join(__dirname, './**/*.entity.ts')],
    synchronize: true,
    logging: false,
    namingStrategy: new ConstraintSnakeNamingStrategy(),
  });
}
