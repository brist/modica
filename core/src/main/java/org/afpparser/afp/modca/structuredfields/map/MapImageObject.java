package org.afpparser.afp.modca.structuredfields.map;

import org.afpparser.afp.modca.structuredfields.SfIntroducer;
import org.afpparser.afp.modca.structuredfields.StructuredFieldWithTripletGroup;
import org.afpparser.afp.modca.triplets.RepeatingTripletGroup;

/**
 * The Map Image Object structured field specifies how an image data object is mapped into its
 * object area.
 */
public class MapImageObject extends StructuredFieldWithTripletGroup {

    public MapImageObject(SfIntroducer introducer, RepeatingTripletGroup tripletGroup) {
        super(introducer, tripletGroup);
    }

    @Override
    public String toString() {
        return getType().toString() + super.toString();
    }
}