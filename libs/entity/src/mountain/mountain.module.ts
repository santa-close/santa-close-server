import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Mountain } from '@app/entity/mountain/mountain.entity';

@Module({ imports: [TypeOrmModule.forFeature([Mountain])] })
export class MountainModule {}
