import { Entity, Column } from 'typeorm';
import { BaseEntity } from '@app/entity/base.entity';

@Entity()
export class Mountain extends BaseEntity {
  @Column()
  name: string;
}
