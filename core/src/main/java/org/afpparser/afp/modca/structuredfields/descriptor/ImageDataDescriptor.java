package org.afpparser.afp.modca.structuredfields.descriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.afpparser.afp.modca.Parameters;
import org.afpparser.afp.modca.common.PresentationSpaceUnits;
import org.afpparser.afp.modca.ioca.IocaFunctionSetId;
import org.afpparser.afp.modca.ioca.SelfDefiningField;
import org.afpparser.afp.modca.ioca.SetBilevelImageColor;
import org.afpparser.afp.modca.ioca.SetExtendedBilevelImageColor;
import org.afpparser.afp.modca.structuredfields.AbstractStructuredField;
import org.afpparser.afp.modca.structuredfields.SfIntroducer;

/**
 * The Image Data Descriptor structured field contains the descriptor data for an image data object.
 */
public class ImageDataDescriptor extends AbstractStructuredField {

    private final PresentationSpaceUnits unitsBase;
    private final int xResol;
    private final int yResol;
    private final int xSize;
    private final int ySize;
    private final List<SelfDefiningField> selfDefiningFields;

    public ImageDataDescriptor(SfIntroducer introducer, Parameters params) {
        super(introducer);
        unitsBase = PresentationSpaceUnits.getValue(params.getByte());
        xResol = params.getUInt(2);
        yResol = params.getUInt(2);
        xSize = params.getUInt(2);
        ySize = params.getUInt(2);
        selfDefiningFields = getFields(params);
    }

    private List<SelfDefiningField> getFields(Parameters params) {
        List<SelfDefiningField> selfDefiningFields = new ArrayList<SelfDefiningField>();
        while (params.getPosition() < params.size()) {
            SelfDefiningField sdf = null;
            switch (params.getByte()) {
            case (byte) 0xF7:
                sdf = new IocaFunctionSetId(params);
                break;
            case (byte) 0xF6:
                sdf = new SetBilevelImageColor(params);
                break;
            case (byte) 0xF4:
                sdf = new SetExtendedBilevelImageColor(params);
                break;
            default:
                throw new IllegalArgumentException("Illegal self defining field given.");
            }
            selfDefiningFields.add(sdf);
        }
        return selfDefiningFields;
    }

    /**
     * The base units of each of the measurements.
     *
     * @return the base units
     */
    public PresentationSpaceUnits getUnitsBase() {
        return unitsBase;
    }

    /**
     * Horizontal resolution in image points per unit base.
     *
     * @return the x-axis resolution
     */
    public int getXResol() {
        return xResol;
    }

    /**
     * Vertical resolution in image points per unit base.
     *
     * @return the y-axis resolution
     */
    public int getYResol() {
        return yResol;
    }

    /**
     * Horizontal size of the image presentation space in image points.
     *
     * @return the x-axis size
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * Vertical size of the image presentation space in image points.
     *
     * @return the y-axis size
     */
    public int getYSize() {
        return ySize;
    }

    /**
     * The self defining fields that this object wraps.
     *
     * @return the fields
     */
    public List<SelfDefiningField> getSelfDefiningFields() {
        return Collections.unmodifiableList(selfDefiningFields);
    }

    private String sdfToString() {
        StringBuilder sb = new StringBuilder();
        for (SelfDefiningField sdf : selfDefiningFields) {
            sb.append("\t" + sdf.toString() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getType().getName() + " xSize=" + xSize + " ySize=" + ySize + sdfToString();
    }
}