
/* First created by JCasGen Sun Feb 21 17:15:08 CET 2021 */
package webanno.custom;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed May 05 23:22:10 CEST 2021
 * @generated */
public class Zielgruppenadressierung_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Zielgruppenadressierung.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("webanno.custom.Zielgruppenadressierung");
 
  /** @generated */
  final Feature casFeat_GroupIndividual;
  /** @generated */
  final int     casFeatCode_GroupIndividual;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getGroupIndividual(int addr) {
        if (featOkTst && casFeat_GroupIndividual == null)
      jcas.throwFeatMissing("GroupIndividual", "webanno.custom.Zielgruppenadressierung");
    return ll_cas.ll_getStringValue(addr, casFeatCode_GroupIndividual);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGroupIndividual(int addr, String v) {
        if (featOkTst && casFeat_GroupIndividual == null)
      jcas.throwFeatMissing("GroupIndividual", "webanno.custom.Zielgruppenadressierung");
    ll_cas.ll_setStringValue(addr, casFeatCode_GroupIndividual, v);}
    
  
 
  /** @generated */
  final Feature casFeat_GroupAffiliation;
  /** @generated */
  final int     casFeatCode_GroupAffiliation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getGroupAffiliation(int addr) {
        if (featOkTst && casFeat_GroupAffiliation == null)
      jcas.throwFeatMissing("GroupAffiliation", "webanno.custom.Zielgruppenadressierung");
    return ll_cas.ll_getStringValue(addr, casFeatCode_GroupAffiliation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGroupAffiliation(int addr, String v) {
        if (featOkTst && casFeat_GroupAffiliation == null)
      jcas.throwFeatMissing("GroupAffiliation", "webanno.custom.Zielgruppenadressierung");
    ll_cas.ll_setStringValue(addr, casFeatCode_GroupAffiliation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_NegativeSentiment;
  /** @generated */
  final int     casFeatCode_NegativeSentiment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getNegativeSentiment(int addr) {
        if (featOkTst && casFeat_NegativeSentiment == null)
      jcas.throwFeatMissing("NegativeSentiment", "webanno.custom.Zielgruppenadressierung");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_NegativeSentiment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNegativeSentiment(int addr, boolean v) {
        if (featOkTst && casFeat_NegativeSentiment == null)
      jcas.throwFeatMissing("NegativeSentiment", "webanno.custom.Zielgruppenadressierung");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_NegativeSentiment, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ImplicitAddressing;
  /** @generated */
  final int     casFeatCode_ImplicitAddressing;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getImplicitAddressing(int addr) {
        if (featOkTst && casFeat_ImplicitAddressing == null)
      jcas.throwFeatMissing("ImplicitAddressing", "webanno.custom.Zielgruppenadressierung");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_ImplicitAddressing);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setImplicitAddressing(int addr, boolean v) {
        if (featOkTst && casFeat_ImplicitAddressing == null)
      jcas.throwFeatMissing("ImplicitAddressing", "webanno.custom.Zielgruppenadressierung");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_ImplicitAddressing, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Zielgruppenadressierung_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_GroupIndividual = jcas.getRequiredFeatureDE(casType, "GroupIndividual", "uima.cas.String", featOkTst);
    casFeatCode_GroupIndividual  = (null == casFeat_GroupIndividual) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_GroupIndividual).getCode();

 
    casFeat_GroupAffiliation = jcas.getRequiredFeatureDE(casType, "GroupAffiliation", "uima.cas.String", featOkTst);
    casFeatCode_GroupAffiliation  = (null == casFeat_GroupAffiliation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_GroupAffiliation).getCode();

 
    casFeat_NegativeSentiment = jcas.getRequiredFeatureDE(casType, "NegativeSentiment", "uima.cas.Boolean", featOkTst);
    casFeatCode_NegativeSentiment  = (null == casFeat_NegativeSentiment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_NegativeSentiment).getCode();

 
    casFeat_ImplicitAddressing = jcas.getRequiredFeatureDE(casType, "ImplicitAddressing", "uima.cas.Boolean", featOkTst);
    casFeatCode_ImplicitAddressing  = (null == casFeat_ImplicitAddressing) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ImplicitAddressing).getCode();

  }
}



    