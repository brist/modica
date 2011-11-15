package org.afpparser.afp.modca;

import java.util.List;

import org.afpparser.afp.modca.triplets.Triplet;

/**
 * A structured field is the building block of an AFP document. 
 */
public interface StructuredField {
    /**
     * Whether or not the extensions data flag is set in the introducer.
     *
     * @return true if the extension data flag is set
     */
    boolean hasExtData();

    /**
     * Whether or not the segmented data flag is set in the introducer.
     *
     * @return true if the segmented flag is set
     */
    boolean hasSegmentedData();

    /**
     * Whether or not the data padding flag is set in the introducer.
     *
     * @return true if the data padding flag is set
     */
    boolean hasDataPadding();

    /**
     * The length of this structured field, this does not include extension data.
     *
     * @return the length
     */
    int getLength();

    /**
     * The type of structured field this object represents.
     *
     * @return structured field type
     */
    SfType getType();

    /**
     * The byte offset of this structured field within the AFP document.
     *
     * @return the byte offset
     */
    long getOffset();

    /**
     * The length of extension data.
     *
     * @return the ext data length
     */
    int getExtLength();

    /**
     * The number of bytes until the next structured field, this is basically the sum of the ext
     * data length and the structured field length.
     *
     * @return the number of bytes to the next structured field
     */
    int bytesToNextStructuredField();

    /**
     * Checks if this structured field has triplets bound to it.
     *
     * @return true if this structured field has triplets 
     */
    boolean hasTriplets();

    /**
     * Gets a view to the triplets as an unmodifiable List.
     *
     * @return the triplets bound to this structured field
     */
    List<Triplet> getTriplets();

}
