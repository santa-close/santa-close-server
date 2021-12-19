import { TypeOrmModule } from '@nestjs/typeorm';
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
    autoLoadEntities: true,
    synchronize: true,
    logging: false,
    namingStrategy: new ConstraintSnakeNamingStrategy(),
  });
}
