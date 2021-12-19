import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Mountain } from '@app/entity/domain/mountain/mountain.entity';

@Module({ imports: [TypeOrmModule.forFeature([Mountain])] })
export class MountainModule {}
