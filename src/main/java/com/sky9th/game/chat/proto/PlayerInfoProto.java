// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PlayerInfo.proto

package com.sky9th.game.chat.proto;

public final class PlayerInfoProto {
  private PlayerInfoProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Sky9th_Protobuf_PlayerInfo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Sky9th_Protobuf_PlayerInfo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Sky9th_Protobuf_Transform_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Sky9th_Protobuf_Transform_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020PlayerInfo.proto\022\017Sky9th.Protobuf\"h\n\nP" +
      "layerInfo\022\021\n\tnetworkID\030\001 \002(\t\022\030\n\004type\030\002 \002" +
      "(\t:\nPlayerInfo\022-\n\ttransform\030\003 \002(\0132\032.Sky9" +
      "th.Protobuf.Transform\"c\n\tTransform\022\t\n\001x\030" +
      "\001 \002(\001\022\t\n\001y\030\002 \002(\001\022\t\n\001z\030\003 \002(\001\022\n\n\002up\030\004 \002(\010\022" +
      "\014\n\004down\030\005 \002(\010\022\014\n\004left\030\006 \002(\010\022\r\n\005right\030\007 \002" +
      "(\010B/\n\032com.sky9th.game.chat.protoB\017Player" +
      "InfoProtoP\001"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Sky9th_Protobuf_PlayerInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Sky9th_Protobuf_PlayerInfo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Sky9th_Protobuf_PlayerInfo_descriptor,
        new java.lang.String[] { "NetworkID", "Type", "Transform", });
    internal_static_Sky9th_Protobuf_Transform_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_Sky9th_Protobuf_Transform_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Sky9th_Protobuf_Transform_descriptor,
        new java.lang.String[] { "X", "Y", "Z", "Up", "Down", "Left", "Right", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
