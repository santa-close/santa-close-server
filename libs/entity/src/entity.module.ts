import { Module } from '@nestjs/common';
import { MountainModule } from '@app/entity/mountain/mountain.module';

@Module({
  imports: [MountainModule],
})
export class EntityModule {}
