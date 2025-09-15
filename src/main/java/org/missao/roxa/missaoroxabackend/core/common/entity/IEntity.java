package org.missao.roxa.missaoroxabackend.core.common.entity;

public interface IEntity<Entity extends IEntityDateInfo> {
    Entity getDateInfo();
}