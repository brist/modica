package org.modica.afp.modca.structuredfields;

import org.modica.afp.modca.StructuredFieldFactory;


/**
 * Structured fields have representative type codes within their identity bytes. These enumerate
 * the various types of structured fields available.
 */
public enum TypeCode {
    Index(0x8C) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createIndex(intro);
        }
    },
    Attribute(0xA0) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    },
    CopyCount(0xA2) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    },
    Descriptor(0xA6) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createDescriptor(intro);
        }
    },
    Control(0xA7) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createControl(intro);
        }
    },
    Begin(0xA8) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createBegin(intro);
        }
    },
    End(0xA9) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createEnd(intro);
        }
    },
    Map(0xAB) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createMap(intro);
        }
    },
    Position(0xAC) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createPosition(intro);
        }
    },
    Process(0xAD) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    },
    Orientation(0xAE) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    },
    Include(0xAF) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createInclude(intro);
        }
    },
    Table(0xB0) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    },
    Migration(0xB1) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createMigration(intro);
        }
    },
    Variable(0xB2) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    },
    Link(0xB4) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    },
    Data(0xEE) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return factory.createData(intro);
        }
    },
    Unknown(0x00) {
        @Override
        public StructuredField createField(StructuredFieldFactory factory,
                StructuredFieldIntroducer intro) {
            return null;
        }
    };
    private final byte typeCode;

    private TypeCode(int typeCode) {
        this.typeCode = (byte) typeCode;
    }

    /**
     * The byte value of the type code.
     *
     * @return the type code byte
     */
    public byte getValue() {
        return typeCode;
    }

    public byte[] getIdForType(byte type) {
        return new byte[] { (byte) 0xD3, typeCode, type };
    }

    public byte[] getIdForType(CategoryCode categoryCode) {
        return new byte[] { (byte) 0xD3, typeCode, categoryCode.getValue() };
    }

    public abstract StructuredField createField(StructuredFieldFactory factory,
            StructuredFieldIntroducer intro);
}
