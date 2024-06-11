//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.9
//
// <auto-generated>
//
// Generated from file `AppInterfaces.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package AppInterfaces;

public interface Server extends com.zeroc.Ice.Object
{
    void solveIntegral(int requestID, Integral integral, com.zeroc.Ice.Current current);

    void testMode(int requestID, Integral integral, String option, String numberFormat, com.zeroc.Ice.Current current);

    void printResponse(String res, com.zeroc.Ice.Current current);

    double getLoad(com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::AppInterfaces::Server",
        "::Ice::Object"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::AppInterfaces::Server";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_solveIntegral(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_requestID;
        final com.zeroc.IceInternal.Holder<Integral> icePP_integral = new com.zeroc.IceInternal.Holder<>();
        iceP_requestID = istr.readInt();
        istr.readValue(v -> icePP_integral.value = v, Integral.class);
        istr.readPendingValues();
        inS.endReadParams();
        Integral iceP_integral = icePP_integral.value;
        obj.solveIntegral(iceP_requestID, iceP_integral, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_testMode(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_requestID;
        final com.zeroc.IceInternal.Holder<Integral> icePP_integral = new com.zeroc.IceInternal.Holder<>();
        String iceP_option;
        String iceP_numberFormat;
        iceP_requestID = istr.readInt();
        istr.readValue(v -> icePP_integral.value = v, Integral.class);
        iceP_option = istr.readString();
        iceP_numberFormat = istr.readString();
        istr.readPendingValues();
        inS.endReadParams();
        Integral iceP_integral = icePP_integral.value;
        obj.testMode(iceP_requestID, iceP_integral, iceP_option, iceP_numberFormat, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_printResponse(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_res;
        iceP_res = istr.readString();
        inS.endReadParams();
        obj.printResponse(iceP_res, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getLoad(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        double ret = obj.getLoad(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeDouble(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "getLoad",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "printResponse",
        "solveIntegral",
        "testMode"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_getLoad(this, in, current);
            }
            case 1:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 5:
            {
                return _iceD_printResponse(this, in, current);
            }
            case 6:
            {
                return _iceD_solveIntegral(this, in, current);
            }
            case 7:
            {
                return _iceD_testMode(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
