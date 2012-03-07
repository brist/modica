package org.modica.afp.modca.structuredfields.descriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modica.afp.ioca.IocaFunctionSetId;
import org.modica.afp.ioca.SelfDefiningField;
import org.modica.afp.ioca.SetBilevelImageColor;
import org.modica.afp.ioca.SetExtendedBilevelImageColor;
import org.modica.afp.modca.ParameterAsString;
import org.modica.afp.modca.Parameters;
import org.modica.afp.modca.common.PresentationSpaceUnits;
import org.modica.afp.modca.structuredfields.AbstractStructuredField;
import org.modica.afp.modca.structuredfields.StructuredFieldIntroducer;

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

    public ImageDataDescriptor(StructuredFieldIntroducer introducer, Parameters params) {
        super(introducer);
        unitsBase = PresentationSpaceUnits.getValue(params.getByte());
        xResol = (int) params.getUInt(2);
        yResol = (int) params.getUInt(2);
        xSize = (int) params.getUInt(2);
        ySize = (int) params.getUInt(2);
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

    @Override
    public List<ParameterAsString> getParameters() {
        List<ParameterAsString> params = new ArrayList<ParameterAsString>();
        params.add(new ParameterAsString("XResolution", xResol));
        params.add(new ParameterAsString("YResolution", yResol));
        params.add(new ParameterAsString("XSize", xSize));
        params.add(new ParameterAsString("YSize", ySize));
        params.add(new ParameterAsString("UnitBase", unitsBase.name()));
        return params;
    }
}
