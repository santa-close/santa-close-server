import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Mountain {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;
}
